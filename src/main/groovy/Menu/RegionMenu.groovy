package Menu

import Services.Olx

class RegionMenu {
    static start(){
        Scanner input = new Scanner(System.in)
        println("Digite o nome do produto: ")
        String item = input.nextLine()
        println("Digite a sigla do estado que quer fazer a busca: ")
        String state = input.nextLine()


        Olx olx = new Olx()
        olx.state = state
        olx.item = item
        while(!(olx.states.contains(state))){
            println("Digite a sigla do estado que quer procurar: ")
            state = input.nextLine()
        }

        def regions = olx.regionDescription()
        for (int i=0 ; i<regions.size(); i++){
            println("Cod." + i + "-" + regions[i])
        }
        println("Digite o Cod da regiao que quer buscar ")
        String index = input.nextLine()


        olx.search(olx.regionLink(Integer.parseInt(index)))

    }
}
