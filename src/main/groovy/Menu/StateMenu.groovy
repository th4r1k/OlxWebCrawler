package Menu

import Services.Olx

class StateMenu {

    static start(){
        Olx olx = new Olx()
        println("Digite o nome do produto: ")
        Scanner input = new Scanner(System.in)
        String item = input.nextLine()


        println("Digite a sigla do estado que quer procurar: ")
        String state = input.nextLine()
        while(!(olx.states.contains(state))){
            println("Digite a sigla do estado que quer procurar: ")
            state = input.nextLine()
        }

        olx.item = item
        olx.state = state
        olx.search(olx.stateLink())

    }


}
