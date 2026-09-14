"""Gera entradas, executa 3 processos por caso e salva dados brutos e medianas."""
import csv
import hashlib
import json
import os
import platform
import statistics
import subprocess
from datetime import datetime, timezone
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
os.chdir(ROOT)
JAVA = os.environ.get("JAVA", "java")
CAMPOS = ["modo", "n", "tempoMontagemMs", "tempoBuscaTelefoneMs",
          "tempoBuscaNomeMs", "tempoRemocaoMs"]
TAMANHOS = [10000, 25000, 50000, 100000]


def executar(*args):
    return subprocess.run([JAVA, "-cp", "bin", *map(str, args)],
                          text=True, capture_output=True, check=True).stdout.strip()


ambiente = {
    "inicio_utc": datetime.now(timezone.utc).isoformat(),
    "sistema": platform.platform(),
    "processador": platform.processor(),
    "java": subprocess.run([JAVA, "-version"], text=True, capture_output=True,
                           check=True).stderr.strip(),
    "seed": 42, "repeticoes": 3, "processo_novo_por_medicao": True,
    "arquivos_sha256": {},
}
for n in TAMANHOS:
    arquivo = Path(f"dados/contatos_{n}.txt")
    print(executar("testes.GeradorDadosContatos", n, arquivo, 42), flush=True)
    ambiente["arquivos_sha256"][arquivo.name] = hashlib.sha256(arquivo.read_bytes()).hexdigest()

linhas = []
with open("dados/resultados_brutos.csv", "w", newline="", encoding="utf-8") as f:
    writer = csv.DictWriter(f, fieldnames=["repeticao", *CAMPOS])
    writer.writeheader()
    for n in TAMANHOS:
        for modo in ("false", "true"):
            for repeticao in range(1, 4):
                saida = executar("testes.BenchmarkListas", f"dados/contatos_{n}.txt", modo)
                linha = dict(zip(CAMPOS, saida.split(",")))
                linha["repeticao"] = repeticao
                writer.writerow(linha)
                f.flush()
                linhas.append(linha)
                print(f"Repeticao {repeticao}: {saida}", flush=True)

with open("dados/resultados.csv", "w", newline="", encoding="utf-8") as f:
    writer = csv.DictWriter(f, fieldnames=CAMPOS)
    writer.writeheader()
    for modo in ("nao-ordenada", "ordenada"):
        for n in TAMANHOS:
            grupo = [r for r in linhas if r["modo"] == modo and int(r["n"]) == n]
            resumo = {"modo": modo, "n": n}
            resumo.update({c: statistics.median(float(r[c]) for r in grupo) for c in CAMPOS[2:]})
            writer.writerow(resumo)
ambiente["fim_utc"] = datetime.now(timezone.utc).isoformat()
Path("dados/ambiente.json").write_text(json.dumps(ambiente, indent=2, ensure_ascii=False), encoding="utf-8")
print("24 execucoes concluidas; resultados.csv contem as medianas.", flush=True)
