# Kit FMS

### =[INSTALAÇÃO PRIMEIRA VEZ]= 
[FAZER SE É A PRIMEIRA VEZ QUE O PC/TABLET TÁ A CORRER ISTO]

1. Fazer download do zip e extrair (https://github.com/TSGouveia/Kit_FMS_Exec) (é suposto o zip rondar os 60 MB)
2. Donwload o apk e tal (RAUL)
3. Abrir o Kit FMS no tablet (O Raúl tem que meter aqui as cenas do apk)
4. Abrir o ficheiro Kit FMS no PC
5. No PC, no canto superior esquerdo clicar no botão "DEBUG". No tablet clicar no botão "CHANGE IP" e colocar o IP que aparece no PC. (O IP do normalmente está certo mas dependendo de PC para PC pode estar errado devido a Virtual Machines e companhias, se estiver errado, ver no cmd com o ipconfig)
6. No PC, no canto superior esquerdo clicar no botão "SETTINGS" colocar o IP do tablet (mostrado na aplicação do tablet) e do Arduino (O Arduino costuma ser 192.168.2.28)
7. Fechar tudo

INSTALAÇÃO CONCLUIDA -> Caso seja mudado o tablet ou o PC, esta instalação tem que ser toda feita outra vez.

### =[COMO CORRER]=
[FAZER SEMPRE QUE É DESLIGADO O KIT FMS/INTELLIJ]

1. Abrir o Kit FMS no PC
2. Abrir o Kit FMS no Tablet
3. Abrir o IntelliJ no PC com o código do André (é recomendado usar o ficheiro que está em .INTELLIJ ANDRE/CTS_ANDRE)
4. Correr o código do André
	4.1 Se desejado, ver os agents a ficarem online no menu "DEBUG" da Unity
5. No Kit FMS do PC, no canto superior esquerdo clicar no botão "CONNECT"
	5.1  Se desejado, ver o arduino a ficar online no menu "DEBUG" da Unity

CONCLUIDO

### =[COMO LANÇAR UM PRODUTO]=
[FAZER SEMPRE QUE DESEJADO]

1. No tablet, dizer em que lado se encontra o operador.
2. No KIT FMS do PC, no canto superior esquerdo clicar no botão "SETUP", dizer quais Skills estão disponiveis em que operadores.
3. No KIT FMS do PC, no canto superior esquerdo clicar no botão "LAUNCH", dizer quais as ações que quer que aconteça no bloco. (O Kit fisico não gosta muito de dar voltas com uma só peça e fica preso nos sitios, é recomendado meter as duas peças na mesma volta)
4. Quando pedido, clicar o tablet a dizer que foi colocada uma peça.

CONCLUIDO

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

## Comunicações
### Arduino -> Unity
[Isto serve para trocar bits no Unity]
Socket com Json\
Ip da Unity -> localhost:8888\
Exemplo de Json:\
{\
 "Port" : "R1_0",\
 "Value" : "1"\
}

### HMI -> Unity
[Isto serve para adicionar um bloco na caixa da Unity]
HTTP Post com Json\
Ip da Unity -> http://localhost:8082/ \
Exemplo de Json:\
{\
 "Position" : "1",\
 "Color" : "Red"\
}

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

## Sensores I Number_Index
| Number | Index | Function         | Active |
|--------|-------|------------------|--------|
| 0      | 0     | Conveyor B       | High   |
| 0      | 1     | Conveyor A       | High   |
| 0      | 2     | Punch Down       | Low    |
| 0      | 3     | Punch Up         | Low    |
| 0      | 4     | Punch Forward    | High   |
| 0      | 5     | Punch Backward   | High   |
| 1      | 0     | Conveyor C       | High   |
| 1      | 1     | Conveyor D       | High   |
| 1      | 2     | Rotate E Give    | Low    |
| 1      | 3     | Rotate E Receive | Low    |
| 1      | 4     | Rotate C Give    | Low    |
| 1      | 5     | Rotate C Receive | Low    |
| 2      | 0     | Conveyor E       | High   |
| 2      | 1     | Conveyor F       | High   |
| 2      | 2     | Conveyor A Left  | Low    |
| 2      | 3     | Conveyor A Right | Low    |
| 2      | 4     | -                | -      |
| 2      | 5     | -                | -      |

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

## Atuadores R Number_Index
| Number | Index | Function             |
|--------|-------|----------------------|
| 0      | 0     | -                    |
| 0      | 1     | -                    |
| 0      | 2     | Punch Up             |
| 0      | 3     | Punch Forward        |
| 0      | 4     | Conveyor E           |
| 0      | 5     | Conveyor E Receive   |
| 0      | 6     | Conveyor C Give      |
| 0      | 7     | Conveyor A Backwards |
| 0      | 8     | Conveyor A Right     |
| 1      | 0     | -                    |
| 1      | 1     | -                    |
| 1      | 2     | -                    |
| 1      | 3     | Punch Down           |
| 1      | 4     | Conveyor F           |
| 1      | 5     | Rotate C Give        |
| 1      | 6     | Conveyor C           |
| 1      | 7     | Conveyor B           |
| 1      | 8     | Conveyor A Left      |
| 2      | 3     | Punch Spin           |
| 2      | 4     | Punch Forward        |
| 2      | 5     | -                    |
| 2      | 6     | Conveyor D           |
| 2      | 7     | Conveyor C Receive   |
| 2      | 8     | Conveyor A Forward   |
