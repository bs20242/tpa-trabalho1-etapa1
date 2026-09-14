import csv
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
from pathlib import Path

base = Path(__file__).parent
saida = base.parent / "relatorio"
saida.mkdir(exist_ok=True)
rows = list(csv.DictReader(open(base / "resultados.csv", encoding="utf-8-sig")))

def serie(modo, campo):
    dados = [(int(r["n"]), float(r[campo])) for r in rows if r["modo"] == modo]
    dados.sort()
    return [d[0] for d in dados], [d[1] for d in dados]

COR_ORD = "#2563eb"
COR_NAOORD = "#dc2626"

# Grafico 1: Tempo de Montagem em funcao de N
plt.figure(figsize=(7, 5))
n1, t1 = serie("nao-ordenada", "tempoMontagemMs")
n2, t2 = serie("ordenada", "tempoMontagemMs")
plt.plot(n1, t1, marker="o", color=COR_NAOORD, label="Lista Nao-Ordenada")
plt.plot(n2, t2, marker="o", color=COR_ORD, label="Lista Ordenada")
plt.xlabel("Quantidade de contatos (N)")
plt.ylabel("Tempo (ms; mediana de 3 execucoes)")
plt.title("Leitura, validacao e montagem das duas listas")
plt.legend()
plt.grid(True, alpha=0.3)
plt.tight_layout()
plt.savefig(saida / "grafico1_montagem.png", dpi=150)
plt.close()

# Grafico 2: Tempo de Busca do ultimo contato em funcao de N
plt.figure(figsize=(7, 5))
n1t, t1t = serie("nao-ordenada", "tempoBuscaTelefoneMs")
n1n, t1n = serie("nao-ordenada", "tempoBuscaNomeMs")
n2t, t2t = serie("ordenada", "tempoBuscaTelefoneMs")
n2n, t2n = serie("ordenada", "tempoBuscaNomeMs")
plt.plot(n1t, t1t, marker="o", color=COR_NAOORD, linestyle="-", label="Nao-Ordenada (busca telefone)")
plt.plot(n1n, t1n, marker="s", color=COR_NAOORD, linestyle="--", label="Nao-Ordenada (busca nome)")
plt.plot(n2t, t2t, marker="o", color=COR_ORD, linestyle="-", label="Ordenada (busca telefone)")
plt.plot(n2n, t2n, marker="s", color=COR_ORD, linestyle="--", label="Ordenada (busca nome)")
plt.xlabel("Quantidade de contatos (N)")
plt.ylabel("Tempo (ms; mediana de 3 execucoes)")
plt.title("Busca do contato unico na cauda das duas listas")
plt.legend()
plt.grid(True, alpha=0.3)
plt.tight_layout()
plt.savefig(saida / "grafico2_busca.png", dpi=150)
plt.close()

# Grafico 3: Tempo de Remocao do ultimo contato em funcao de N
plt.figure(figsize=(7, 5))
n1, t1 = serie("nao-ordenada", "tempoRemocaoMs")
n2, t2 = serie("ordenada", "tempoRemocaoMs")
plt.plot(n1, t1, marker="o", color=COR_NAOORD, label="Lista Nao-Ordenada")
plt.plot(n2, t2, marker="o", color=COR_ORD, label="Lista Ordenada")
plt.xlabel("Quantidade de contatos (N)")
plt.ylabel("Tempo (ms; mediana de 3 execucoes)")
plt.title("Remocao da cauda na lista por telefone")
plt.legend()
plt.grid(True, alpha=0.3)
plt.tight_layout()
plt.savefig(saida / "grafico3_remocao.png", dpi=150)
plt.close()

print("Graficos gerados com sucesso.")
