package Menu

import Services.Olx

class SimpleMenu {

    static start(){
        println("Digite o nome do produto: ")
        Scanner input = new Scanner(System.in)
        String item = input.nextLine()

        Olx olx = new Olx(item: item)
        olx.search(olx.simpleLink())



    }
}
