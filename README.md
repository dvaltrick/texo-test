# texo-test
Este teste foi desenvolvido utilizando a linguagem Java, junto com a ferramenta Maven e os frameworks Spring e Hibernate, 
para realizar a programação foi utilizada a IDE Spring Tool Suite.

# Importação e Configuração
* O projeto deve ser importado para dentro de uma IDE utilizando o recurso "Importar como projeto Maven", deve ser escolhido o pom.xml da aplicação.
* Testes, ao executar os testes o mesmo irá criar um banco de dados H2 em memória para realizar as operações.
* Para configurar o banco de dados da aplicação vá até o diretório "texo-test/src/main/resources" e altere o 
arquivo "application.properties", por padrão deixei configurado para que sempre ao inicializar o banco de dados seja recriado, 
porém isso pode ser alterado modificando a propriedade "spring.jpa.hibernate.ddl-auto" para "update", a propriedade "spring.datasource.url" informa a string 
de conexão com o banco de dados, por padrão utilizei o MySQL. <br/><br/>
spring.jpa.hibernate.ddl-auto=create-drop<br/>
spring.datasource.url=jdbc:mysql://localhost:3306/dbcities?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC<br/>
spring.datasource.username=root<br/>
spring.datasource.password=root<br/>

# Funcionamento
* Ao inicializar a aplicação, o programa irá verificar se a base de dados possuí dados, se não possuir irá alimenta-la com os dados do arquivo cidades.csv que se encontra na pasta resources.

# Documentação da Api
* Obter lista de todas as cidades que são capitais:
<br/>URL: /api/states/capitals
<br/>Método: GET
<br/>Exemplo http://localhost:8080/api/states/capitals

* Obter lista e quantidade de cidades filtradas por um atributo>
<br/>URL: /api/cities
<br/>Método: GET
<br/>Parâmetros aceitos na URL: ibge_id, uf, name, lon, lat, no_accent, alternative_names, microregion, mesoregion
<br/>Exemplo: http://localhost:8080/api/cities?microregion=Joinville&uf=SC

* Obter o estado com a maior e menor quantidade de cidades:
<br/>URL: /api/cities/moreandless
<br/>Método: GET
<br/>Exemplo http://localhost:8080/api/cities/moreandless

* Obter uma página da lista total de cidades:
<br/>URL: /api/cities/{page}
<br/>Método: GET
<br/>Exemplo http://localhost:8080/api/cities/10 -> retorna a décima página

* Permitir remover uma cidade existente:
<br/>URL: /api/cities/{id}
<br/>Método: DELETE
<br/>Exemplo http://localhost:8080/api/cities/4447 -> excluí a cidade com ID 4447 
