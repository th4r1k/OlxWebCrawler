package Services

import static groovyx.net.http.HttpBuilder.configure
import org.jsoup.nodes.Document


class Olx {

    String olxLink = "https://www.olx.com.br/"
    String item = "iphone 11"
    String state = "brasil"
    String[] states = ["ac", "al", "ap", "am", "ba", "ce", "df", "es", "go", "ma", "mt", "ms", "mg", "pa", "pb", "pr", "pe", "pi", "rj", "rn", "rs", "ro", "rr", "sc", "sp", "se", "to"]


    public simpleLink() {
        String linkPagina1 = olxLink + state + "?q=" + item.replaceAll(" ", "%20") + "&sp=2"
        String linkPagina2 = olxLink + state + "?o=2&q=" + item.replaceAll(" ", "%20") + "&sp=2"
        String linkPagina3 = olxLink + state + "?o=3&q=" + item.replaceAll(" ", "%20") + "&sp=2"

        return [linkPagina1, linkPagina2, linkPagina3]
    }

    public stateLink() {
        if (states.contains(state)) {
            String linkPagina1 = olxLink + "estado-" + state + "?q=" + item.replaceAll(" ", "%20") + "&sp=2"
            String linkPagina2 = olxLink + "estado-" + state + "?o=2&q=" + item.replaceAll(" ", "%20") + "&sp=2"
            String linkPagina3 = olxLink + "estado-" + state + "?o=3&q=" + item.replaceAll(" ", "%20") + "&sp=2"

            return [linkPagina1, linkPagina2, linkPagina3]
        }
    }

    public regionDescription() {
        Document page1 = configure { request.uri = olxLink + "estado-" + state }.get()
        def textRegions = page1.getElementsByClass("sc-1l6qrj6-0 hSmLZl sc-gzVnrw KJfcY").text()

        def regions = textRegions.split(/[^D][D]{3}/)

        return regions
    }

    public regionLink(int index) {
        Document page1 = configure { request.uri = olxLink + "estado-" + state }.get()
        String link = page1.getElementsByClass("sc-1l6qrj6-0 hSmLZl sc-gzVnrw KJfcY")[index].attr("href")
        String link1 = link + "?q=" + item.replaceAll(" ", "%20") + "&sp=2"
        String link2 = link + "?o=2&q=" + item.replaceAll(" ", "%20") + "&sp=2"
        String link3 = link + "?o=3&q=" + item.replaceAll(" ", "%20") + "&sp=2"

        return [link1, link2, link3]
    }

    public search(def link) {
        Document page1 = configure { request.uri = link[0] }.get()

        def titlePag1 = page1.getElementsByClass("kgl1mq-0 eFXRHn sc-ifAKCX iUMNkO")
        def enderecoPage1 = page1.getElementsByClass("sc-1c3ysll-1 cLQXSQ sc-ifAKCX lgjPoE")
        def urlPage1 = page1.getElementsByClass("sc-12rk7z2-1 huFwya sc-gzVnrw KJfcY")
        def valorPage1 = page1.getElementsByClass("m7nrfa-0 eJCbzj sc-ifAKCX jViSDP")

        Document page2 = configure { request.uri = link[1] }.get()

        def titlePag2 = page2.getElementsByClass("kgl1mq-0 eFXRHn sc-ifAKCX iUMNkO")
        def enderecoPage2 = page2.getElementsByClass("sc-1c3ysll-1 cLQXSQ sc-ifAKCX lgjPoE")
        def urlPage2 = page2.getElementsByClass("sc-12rk7z2-1 huFwya sc-gzVnrw KJfcY")
        def valorPage2 = page2.getElementsByClass("m7nrfa-0 eJCbzj sc-ifAKCX jViSDP")

        Document page3 = configure { request.uri = link[2] }.get()

        def titlePag3 = page3.getElementsByClass("kgl1mq-0 eFXRHn sc-ifAKCX iUMNkO")
        def enderecoPage3 = page3.getElementsByClass("sc-1c3ysll-1 cLQXSQ sc-ifAKCX lgjPoE")
        def urlPage3 = page3.getElementsByClass("sc-12rk7z2-1 huFwya sc-gzVnrw KJfcY")
        def valorPage3 = page3.getElementsByClass("m7nrfa-0 eJCbzj sc-ifAKCX jViSDP")


        int quantidadeDeItens = valorPage1.size() + valorPage2.size() + valorPage3.size()
        int somaValores = 0

        if (valorPage1.text()) {
            for (int i = 0; i < titlePag1.size(); i++) {
                somaValores += Integer.parseInt(valorPage1[i].text().replace(".", "").minus(/R$ /))
            }
        }

        if (valorPage2.text()) {
            for (int i = 0; i < titlePag2.size(); i++) {
                somaValores += Integer.parseInt(valorPage2[i].text().replace(".", "").minus(/R$ /))
            }
        }

        if (valorPage3.text()) {
            for (int i = 0; i < titlePag3.size(); i++) {
                somaValores += Integer.parseInt(valorPage3[i].text().replace(".", "").minus(/R$ /))
            }
        }


        BigDecimal precoMedio = (somaValores / quantidadeDeItens).round(3)
        println("")
        println("O produto mais barato encontrado foi:")
        println("Descricao:" + titlePag1[0].attr("title"))
        println("Valor:" + valorPage1[0].text())
        println("Endereco:" + enderecoPage1[0].text())
        println("Link: " + urlPage1[0].attr("href"))

        println("O preco medio dos resultado encontrados: R\$" + precoMedio)
        println("Veja os resultados das pesquisas salvo em src/main/groovy/downloads")


        File searchResult = new File("./downloads/${item}${state}.csv")
        FileWriter fw = new FileWriter(searchResult, true)
        fw.write("título do anúncio" + "," + "valor" + "," + "endereço" + "," + "URL do anúncio")
        fw.append("\n")
        for (int i = 0; i < titlePag1.size(); i++) {
            if (Integer.parseInt(valorPage1[i].text().replace(".", "").minus(/R$ /)) < precoMedio) {
                fw.write(titlePag1[i].attr("title") + "," + valorPage1[i].text() + "," + enderecoPage1[i].text() + "," + urlPage1[i].attr("href"))
                fw.append("\n")
            }
        }

        for (int i = 0; i < titlePag2.size(); i++) {
            if (Integer.parseInt(valorPage2[i].text().replace(".", "").minus(/R$ /)) < precoMedio) {
                fw.write(titlePag2[i].attr("title") + "," + valorPage2[i].text() + "," + enderecoPage2[i].text() + "," + urlPage2[i].attr("href"))
                fw.append("\n")

            }
        }
        for (int i = 0; i < titlePag3.size(); i++) {
            if (Integer.parseInt(valorPage3[i].text().replace(".", "").minus(/R$ /)) < precoMedio) {
                fw.write(titlePag3[i].attr("title") + "," + valorPage3[i].text() + "," + enderecoPage3[i].text() + "," + urlPage3[i].attr("href"))
                fw.append("\n")

            }
        }
        fw.close()

    }

}
