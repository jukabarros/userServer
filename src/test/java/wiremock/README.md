# Wiremock

O wiremock está simulando um serviço externo que esta aplicação se consome.

Neste caso, a aplicação externa é uma API que retorna dados de perfis que os usuários podem pertencer.

Segue os paths mapeados pelo wiremock (ver arquivos em src/test/resources/wiremock.profiles)

1. /externalService/profile - Retorna todos os perfis (Status 200)

2. /externalService/profile/1 - Retorna o perfil referente ao ID 1 (Administrador) (Status 200)

3. /externalService/profile/?priority=2 - Retorna todos os perfis filtrados pela prioridade = 2

4. /externalService/profile/999 - Retorna Not Found (Status 404)

5. /externalService/profile/abc - Retorna Bad Request (Status 400)


No diretório src/test/resources/wiremock.profiles/__files tem os Json que serão retornados pelo serviço externo.