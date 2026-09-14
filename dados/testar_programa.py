"""Testes do menu real. Execute depois de compilar o Java."""
import os
import subprocess

JAVA = os.environ.get("JAVA") or (r"C:\Users\BPC\.jdks\corretto-21.0.6\bin\java.exe" if os.path.exists(r"C:\Users\BPC\.jdks\corretto-21.0.6\bin\java.exe") else "java")


def executar(modo, comandos):
    resultado = subprocess.run(
        [JAVA, "-cp", "bin", "app.ProgramaContatos"],
        input="\n".join([str(modo), *comandos, "7", ""]),
        text=True, capture_output=True, check=True,
    )
    return resultado.stdout


for modo in (1, 2):
    saida = executar(modo, ["2", "Ana", "111", "2", "Ana", "222",
                            "5", "111", "3", "Ana", "4", "111", "4", "222"])
    assert "Contato encontrado: Ana - 111" not in saida, saida
    assert "Contato encontrado: Ana - 222" in saida, saida
    assert "Quantidade total atual de contatos: 1" in saida, saida

    saida = executar(modo, ["2", "Ana", "111", "2", "Ana", "222",
                            "6", "Ana", "Bia", "333", "3", "Ana", "4", "333"])
    assert "Contato encontrado: Bia - 333" in saida, saida
    assert "Contato encontrado: Ana - 111" in saida, saida
    assert "Quantidade total atual de contatos: 2" in saida, saida

    saida = executar(modo, ["2", "Ana", "111", "2", "Bia", "222",
                            "2", "Outra", "111", "6", "Ana", "Ana", "222",
                            "4", "111", "4", "222"])
    assert "Contato encontrado: Ana - 111" in saida, saida
    assert "Contato encontrado: Bia - 222" in saida, saida
    saida = executar(modo, ["2", "Ana", "111", "5", "111", "2", "Bia", "111", "4", "111"])
    assert "Contato adicionado com sucesso!" in saida, saida
    assert "Contato encontrado: Bia - 111" in saida, saida
    assert "Quantidade total atual de contatos: 1" in saida, saida

print("TESTES_DO_MENU_PASSARAM")
