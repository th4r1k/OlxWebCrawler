package Menu

class InitialMenu {

    static start(){
        println("1 - Pesquisar por todo o Brasil")
        println("2 - Pesquisar por Estado")
        println("3 - Pesquisar por uma regiao de um estado")
        println("0 - Sair")

       println("Digite o codigo do comando: ")
        Scanner input = new Scanner(System.in)
        String data = input.nextLine()

        switch (data) {

            case "1":
                SimpleMenu.start()
                goBack()
                break

            case "2": {
                StateMenu.start()
                goBack()
                break
            }

            case "3":
                RegionMenu.start()
                goBack()
                break


            case "0": {
                input.close()
                System.out.println("Volte sempre!")
                break
            }

            default:
                System.out.println("Opcao invalida")
                start()
        }
    }
    static goBack() {

        System.out.println("")
        System.out.println("________________________________")
        System.out.println("Digite 1 para voltar ao menu")
        Scanner input = new Scanner(System.in)
        String data = input.nextLine()

        switch (data) {
            case "1":
                start()
                break

            default:
                input.close()
                break
        }


    }

}
