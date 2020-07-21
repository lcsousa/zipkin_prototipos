--------------------- SUCESSO -----------------------
Verbo: POST
Url:http://localhost:8081/pessoa/v1
Body:
{
    "nome":"Luis de Jah",
    "cpf":"12345678900",
    "endereco":{
        "cep":"40050001"
    }
}

--------------------- ERRO NÂO TRATADO-------------
Verbo: POST
Url:http://localhost:8081/pessoa/v1
Body:
{
    "nome":"Luis de Jah",
    "cpf":"12345678900",
    "endereco":{
        "cep":"40050001"
    }
}

--------------------- ERRO BEAM VALIDATE-------------
Verbo: POST
Url:http://localhost:8081/pessoa/v2
Body:
{
    "nome":"Luis de Jah",
    "cpf":"12345678900"
}