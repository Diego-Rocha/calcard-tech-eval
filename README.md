## Requisitos p/ construção e execução da aplicação
* Linux: Ubuntu Linux ou distro baseada
* OpenJDK: 1.8
* Maven: 3.5.2
* Docker
* Docker Compose

Antes e necessário construir o [módulo de arquitetura](https://github.com/Diego-Rocha/diego-library) (Instruções de compilação no mesmo)

## Console

Acessando o console:
```bash
./console
```
Uma tela semelhante a esta deve ser apresentada:
```text
Função:
[b] - Build
   [f/q] - Full/Quick
[d] - Docker
   [u/d] - Up/Down
[l] - Limpar Base
```

## Base de Dados
* Tipo: Postgres
* Host: localhost:5432
* Base: diego_tech
* Usuário: diego_tech
* Senha: 123456

## Execução e construção da aplicaçao
### 1º Subir a base de dados
sobe o docker com uma base de dados postgres vazia.

Obs.: é extremamente importante que a base esteja ativa para construção da aplicação, pois a mesma é utilizada pelo Hibernate Reverse Engine para gerar as entidades da aplicação.
```bash
./console d u b
```

## 2º Construir a aplicação
```bash
./console b f
```

# 3º subir a aplicação 
criará mais 2 container:
 - nginx como loadbalance -> host: http://localhost
 - servidor springboot -> sem acesso externo
 ```bash
 ./console d u f
 ```
