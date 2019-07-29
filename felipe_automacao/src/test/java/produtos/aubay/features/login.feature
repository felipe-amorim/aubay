Feature: Testes de validação de input dos campo Usuário e Senha e realização de Login

  @Login
  Scenario: Testes login efetuado com sucesso, Autor: Felipe_Amorim, Categoria: Login
    Given given! O usuário está no portal "www.aubay.com/login"
    When when! O usuário insere o usuário "admin123"
    And and! O usuário insere a senha "passw123"
    And and! O usuário clica em realizar login
    Then then! A página de login efetuado com sucesso é exibida

  @InputPositivo
  Scenario Outline: Testes de inputs com dados positivos, Autor: Felipe_Amorim, Categoria: Inputs_Positivos
    Given given! O usuário está no portal "www.aubay.com/login"
    When when! O usuário insere o usuário <user>
    And and! O usuário insere a senha <password>
    Then then! Os inputs não exibem mensagem de erro

    Examples:
      | user          | password          |
      | "admin123"    | "passw123"        |
      | "aaaaaaa1"    | "passw123"        |
      | "1234567a"    | "passw123"        |
      | "admin123"    | "aaaaaaa1"        |
      | "admin123"    | "1234567a"        |


  @InputNegativo
  Scenario Outline: Testes de inputs com dados negativos, Autor: Felipe_Amorim, Categoria: Inputs_Negativos
    Given given! O usuário está no portal "www.aubay.com/login"
    When when! O usuário insere o usuário <user>
    And and! O usuário insere a senha <password>
    Then then! Os inputs exibem mensagem de erro

    Examples:
      | user          | password          |
      | "aaaaaaaa"    | "passw123"        |
      | "12345678"    | "passw123"        |
      | "123456a"     | "passw123"        |
      | "admin123"    | "aaaaaaaa"        |
      | "admin123"    | "12345678"        |
      | "admin123"    | "123456a"         |

