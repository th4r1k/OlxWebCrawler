import Menu.InitialMenu


static void main(String[] args) {

    File downloadFolder = new File("./downloads")
    if(downloadFolder){
        downloadFolder.deleteDir()
    }
    downloadFolder.mkdir()

    InitialMenu.start()

}
