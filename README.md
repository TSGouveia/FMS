# Kit FMS

=[INSTALAÇÃO PRIMEIRA VEZ]= 
[FAZER SE É A PRIMEIRA VEZ QUE O PC/TABLET TÁ A CORRER ISTO]

1. Fazer download do zip e extrair (https://github.com/TSGouveia/Kit_FMS_Exec) (é suposto o zip rondar os 60 MB)
2. Donwload o apk e tal (RAUL)
3. Abrir o Kit FMS no tablet (O Raúl tem que meter aqui as cenas do apk)
4. Abrir o ficheiro Kit FMS no PC
5. No PC, no canto superior esquerdo clicar no botão "DEBUG". No tablet clicar no botão "CHANGE IP" e colocar o IP que aparece no PC. (O IP do normalmente está certo mas dependendo de PC para PC pode estar errado devido a Virtual Machines e companhias, se estiver errado, ver no cmd com o ipconfig)
6. No PC, no canto superior esquerdo clicar no botão "SETTINGS" colocar o IP do tablet (mostrado na aplicação do tablet) e do Arduino (O Arduino costuma ser 192.168.2.28)
7. Fechar tudo

INSTALAÇÃO CONCLUIDA -> Caso seja mudado o tablet ou o PC, esta instalação tem que ser toda feita outra vez.

=[COMO CORRER]=
[FAZER SEMPRE QUE É DESLIGADO O KIT FMS/INTELLIJ]

1. Abrir o Kit FMS no PC
2. Abrir o Kit FMS no Tablet
3. Abrir o IntelliJ no PC com o código do André (é recomendado usar o ficheiro que está em .INTELLIJ ANDRE/CTS_ANDRE)
4. Correr o código do André
	4.1 Se desejado, ver os agents a ficarem online no menu "DEBUG" da Unity
5. No Kit FMS do PC, no canto superior esquerdo clicar no botão "CONNECT"
	5.1  Se desejado, ver o arduino a ficar online no menu "DEBUG" da Unity

CONCLUIDO

=[COMO LANÇAR UM PRODUTO]=
[FAZER SEMPRE QUE DESEJADO]

1. No tablet, dizer em que lado se encontra o operador.
2. No KIT FMS do PC, no canto superior esquerdo clicar no botão "SETUP", dizer quais Skills estão disponiveis em que operadores.
3. No KIT FMS do PC, no canto superior esquerdo clicar no botão "LAUNCH", dizer quais as ações que quer que aconteça no bloco. (O Kit fisico não gosta muito de dar voltas com uma só peça e fica preso nos sitios, é recomendado meter as duas peças na mesma volta)
4. Quando pedido, clicar o tablet a dizer que foi colocada uma peça.

CONCLUIDO

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

## Comunicações
Arduino -> Unity
Socket com Json
Ip da Unity -> localhost:8888
Exemplo de Json:
}
 "Port" : "R1_0"
 "Value" : "1"
}

HMI -> Unity
HTTP Post com Json
Ip da Unity -> http://localhost:8082
Exemplo de Json:
}
 "Position" : "1"
 "Color" : "Red"
}
