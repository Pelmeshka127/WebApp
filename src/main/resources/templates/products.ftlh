<!DOCTYPE html>
<html>

<head>
  <title>Candy Shop</title>
</head>

<body>

<h1>Candy Shop</h1><hr>
  <img src="/images/main.jpg" height="400px" width="350px"/><br><br>
<form action="/" method="get">
Поиск по названию объявления: <input type="text" name="title"><br>
<input type="submit" value="Поиск"/>
</form>
<#list products as product>
  <div>
    <p><b>${product.title}</b> ${product.price} руб. | <a href="/product/${product.id}">Подробнее...</a></p>
  </div>
<#else>
<h3>Товаров нет</h3>
</#list>
<hr>

<h3>Создать новый товар</h3>
  <form action="/product/create" method="post" enctype="multipart/form-data">
    Название объявления: <input type="text" name="title"/><br><br>
    Описание объявления: <input type="text" name="description"/><br><br>
    Цена: <input type="number" name="price"/><br><br>
    Первая фотография: <input type="file" name="file1"/><br><br>
    <input type="submit" value="Добавить товар"/>
  </form>

</body>

</html>