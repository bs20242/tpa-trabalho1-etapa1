"""Gera o PDF a partir de RELATORIO.md, sem manter uma segunda versao do texto."""
import re
import textwrap
from pathlib import Path
from xml.sax.saxutils import escape

import matplotlib
from reportlab.lib import colors
from reportlab.lib.enums import TA_LEFT
from reportlab.lib.pagesizes import A4
from reportlab.lib.styles import ParagraphStyle
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont
from reportlab.platypus import (SimpleDocTemplate, Paragraph, Preformatted, Spacer,
                               Table, TableStyle, Image, PageBreak, CondPageBreak)

ROOT = Path(__file__).resolve().parent.parent
FONTS = Path(matplotlib.get_data_path()) / "fonts/ttf"
for nome, arquivo in [("Texto", "DejaVuSans.ttf"), ("Texto-Bold", "DejaVuSans-Bold.ttf"),
                      ("Texto-Italic", "DejaVuSans-Oblique.ttf"), ("Codigo", "DejaVuSansMono.ttf")]:
    pdfmetrics.registerFont(TTFont(nome, str(FONTS / arquivo)))
pdfmetrics.registerFontFamily("Texto", normal="Texto", bold="Texto-Bold", italic="Texto-Italic", boldItalic="Texto-Bold")

AZUL = colors.HexColor("#234e70")
styles = {
    "normal": ParagraphStyle("normal", fontName="Texto", fontSize=9.5, leading=14,
                             spaceAfter=8, alignment=TA_LEFT),
    "h1": ParagraphStyle("h1", fontName="Texto-Bold", fontSize=19, leading=25,
                         textColor=AZUL, spaceAfter=18, keepWithNext=True),
    "h2": ParagraphStyle("h2", fontName="Texto-Bold", fontSize=15, leading=21,
                         textColor=AZUL, spaceBefore=12, spaceAfter=12, keepWithNext=True),
    "h3": ParagraphStyle("h3", fontName="Texto-Bold", fontSize=11.5, leading=17,
                         spaceBefore=12, spaceAfter=8, keepWithNext=True),
    "cell": ParagraphStyle("cell", fontName="Texto", fontSize=8, leading=11),
    "code": ParagraphStyle("code", fontName="Codigo", fontSize=7, leading=10,
                           leftIndent=7, backColor=colors.HexColor("#f3f5f7"), spaceAfter=10),
    "cover_kicker": ParagraphStyle("cover_kicker", fontName="Texto-Bold", fontSize=11,
                                    leading=15, textColor=colors.white, spaceAfter=14),
    "cover_title": ParagraphStyle("cover_title", fontName="Texto-Bold", fontSize=27,
                                   leading=34, textColor=colors.white, spaceAfter=14),
    "cover_subtitle": ParagraphStyle("cover_subtitle", fontName="Texto", fontSize=12,
                                      leading=18, textColor=colors.HexColor("#dce9f2"), spaceAfter=10),
    "cover_label": ParagraphStyle("cover_label", fontName="Texto-Bold", fontSize=8,
                                   leading=11, textColor=colors.HexColor("#5c6b78")),
    "cover_value": ParagraphStyle("cover_value", fontName="Texto", fontSize=10,
                                   leading=14, textColor=colors.HexColor("#17232d")),
}


def inline(texto):
    texto = escape(texto)
    texto = re.sub(r"`([^`]+)`", r'<font name="Codigo">\1</font>', texto)
    texto = re.sub(r"\*\*([^*]+)\*\*", r"<b>\1</b>", texto)
    texto = re.sub(r"\*([^*]+)\*", r"<i>\1</i>", texto)
    texto = re.sub(r"\[([^\]]+)\]\(([^)]+)\)", r'<link href="\2" color="#234e70">\1</link>', texto)
    return texto


def rodape(canvas, doc):
    canvas.saveState()
    canvas.setStrokeColor(colors.HexColor("#d4dce3"))
    canvas.line(44, 38, A4[0] - 44, 38)
    canvas.setFont("Texto", 7)
    canvas.setFillColor(colors.HexColor("#505a64"))
    canvas.drawString(44, 26, "TPA - Trabalho 1 | Listas e análise de complexidade")
    canvas.drawRightString(A4[0] - 44, 26, f"Página {doc.page}")
    canvas.restoreState()


def capa(canvas, doc):
    canvas.saveState()
    largura, altura = A4
    canvas.setFillColor(AZUL)
    canvas.rect(0, altura - 265, largura, 265, fill=1, stroke=0)
    canvas.setFillColor(colors.HexColor("#3d769e"))
    canvas.circle(largura - 28, altura - 28, 112, fill=1, stroke=0)
    canvas.setFillColor(colors.HexColor("#2b5e83"))
    canvas.circle(largura - 76, altura - 218, 72, fill=1, stroke=0)
    canvas.setFillColor(colors.HexColor("#d7e7f1"))
    canvas.rect(44, altura - 267, 112, 3, fill=1, stroke=0)
    canvas.restoreState()
    rodape(canvas, doc)


linhas = (ROOT / "RELATORIO.md").read_text(encoding="utf-8").splitlines()
story = []
# A primeira folha funciona como capa; o conteúdo começa na seção 1.
story.extend([
    Spacer(1, 16),
    Paragraph("RELATÓRIO TÉCNICO", styles["cover_kicker"]),
    Paragraph("Trabalho 1 - Listas e análise<br/>de complexidade", styles["cover_title"]),
    Paragraph("Técnicas de Programação Avançada", styles["cover_subtitle"]),
    Spacer(1, 145),
    Table([
        [Paragraph("INSTITUIÇÃO", styles["cover_label"]), Paragraph("Instituto Federal do Espírito Santo - Campus Serra", styles["cover_value"])],
        [Paragraph("CURSO", styles["cover_label"]), Paragraph("Sistemas de Informação", styles["cover_value"])],
        [Paragraph("PROFESSOR", styles["cover_label"]), Paragraph("Victorio Albani de Carvalho", styles["cover_value"])],
        [Paragraph("INTEGRANTES", styles["cover_label"]), Paragraph("Bernardo Simão Rosa, Levi Monteiro e Matheus Abreu", styles["cover_value"])],
        [Paragraph("REPOSITÓRIO", styles["cover_label"]), Paragraph("github.com/bs20242/tpa-trabalho1-etapa1", styles["cover_value"])],
    ], colWidths=[92, 360], hAlign="LEFT", style=TableStyle([
        ("LINEBELOW", (0, -1), (-1, -1), 0.6, colors.HexColor("#d4dce3")),
        ("VALIGN", (0, 0), (-1, -1), "TOP"),
        ("TOPPADDING", (0, 0), (-1, -1), 8),
        ("BOTTOMPADDING", (0, 0), (-1, -1), 8),
    ])),
])
i = next(index for index, linha in enumerate(linhas) if linha.startswith("## 1."))
while i < len(linhas):
    linha = linhas[i].strip()
    if not linha or linha == "---":
        i += 1
        continue
    if linha.startswith("```"):
        bloco = []
        i += 1
        while i < len(linhas) and not linhas[i].startswith("```"):
            # Continuacoes visuais nao recebem numero de linha novo.
            partes = textwrap.wrap(linhas[i], width=108, subsequent_indent="      ",
                                   replace_whitespace=False, drop_whitespace=False)
            bloco.extend(partes or [""])
            i += 1
        story.append(Preformatted("\n".join(bloco), styles["code"]))
    elif linha.startswith("|"):
        rows = []
        primeiro_cabecalho = linha
        while i < len(linhas) and linhas[i].strip().startswith("|"):
            cells = [c.strip() for c in linhas[i].strip().strip("|").split("|")]
            if not all(re.fullmatch(r":?-+:?", c) for c in cells):
                rows.append([Paragraph(inline(c), styles["cell"]) for c in cells])
            i += 1
        largura = A4[0] - 88
        colunas = len(rows[0])
        if colunas == 3 and "Linhas" in primeiro_cabecalho:
            widths = [70, 275, largura - 345]
        elif colunas == 2:
            widths = [135, largura - 135]
        elif colunas == 5:
            widths = [75] + [(largura - 75) / 4] * 4
        else:
            widths = [largura / colunas] * colunas
        tabela = Table(rows, colWidths=widths, repeatRows=1, hAlign="LEFT")
        tabela.setStyle(TableStyle([
            ("BACKGROUND", (0, 0), (-1, 0), colors.HexColor("#e7eef4")),
            ("ROWBACKGROUNDS", (0, 1), (-1, -1), [colors.white, colors.HexColor("#f7f9fb")]),
            ("GRID", (0, 0), (-1, -1), 0.4, colors.HexColor("#d4dce3")),
            ("VALIGN", (0, 0), (-1, -1), "TOP"),
            ("TOPPADDING", (0, 0), (-1, -1), 6),
            ("BOTTOMPADDING", (0, 0), (-1, -1), 6),
        ]))
        story.extend([tabela, Spacer(1, 10)])
        continue
    elif linha.startswith("!["):
        match = re.fullmatch(r"!\[([^]]+)\]\(([^)]+)\)", linha)
        story.append(CondPageBreak(355))
        story.append(Paragraph(inline(match[1]), styles["h3"]))
        story.append(Image(str(ROOT / match[2]), width=380, height=272))
        story.append(Spacer(1, 4))
    elif linha.startswith("#"):
        nivel = len(linha) - len(linha.lstrip("#"))
        if nivel == 2:
            story.append(PageBreak())
        elif linha == "### 3.3 Gráficos":
            story.append(PageBreak())
        elif nivel == 3:
            story.append(CondPageBreak(160))
        story.append(Paragraph(inline(linha[nivel:].strip()), styles[f"h{min(nivel, 3)}"]))
    elif linha.startswith(("- ", "* ")):
        story.append(Paragraph("• " + inline(linha[2:]), styles["normal"]))
    else:
        story.append(Paragraph(inline(linha), styles["normal"]))
    i += 1

destino = ROOT / "relatorio/Relatorio_Trabalho1_TPA.pdf"
doc = SimpleDocTemplate(str(destino), pagesize=A4, leftMargin=44, rightMargin=44,
                        topMargin=42, bottomMargin=52,
                        title="Trabalho 1 - Listas e análise de complexidade",
                        author="Bernardo Simão Rosa, Levi Monteiro e Matheus Abreu")
doc.build(story, onFirstPage=capa, onLaterPages=rodape)
print(f"PDF gerado: {destino}")
