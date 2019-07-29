# Aubay

O projeto de automação segue na pasta felipe_automacao

O projeto é Maven e foi feito na IDE intellij, não necessita de configurações adicionais, apenas abrir, esperar as dependências e rodas pela classe "produtos.aubay.suites.Runner.java"

O comando maven para a execução do projeto é: mvn clean test -DfailIfNoTests=false -Dtest=produtos.aubay.suites.Runner

O projeto segue considerando os paradigmas de programação:

-Page objects

-BDD

-Hierarquia (Classe executora extendendo classe Web)

-Abstração (Classe web com metodos generalizados)


O projeto gera como artefato final, um relatório html no diretório: src\test\resources\jenkins\anexos\Relatorio_detalhado_testes.html
