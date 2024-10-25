<!DOCTYPE html>
<html>

<head>
  <title>Candy Shop</title>
</head>

<body>

<h1>Candy Shop</h1><hr>
  <img src="/sourceimages/main.jpg" height="400px" width="350px"/><br><br>
<hr>
<form action="/" method="get">
<#list products as product>
  <div>
    <p>
      <b>${product.title}</b> ${product.price} руб. |
      <a href="/product/${product.id}">Подробнее...</a><br>
      <img src="/previewImage/${product.id}" height="120px"/><br><br>
      <hr>
    </p>
  </div>
<#else>
<h3>Товаров нет</h3>
</#list>
</form>

<h3>Создать новый товар</h3>
  <form action="/product/create" method="post" enctype="multipart/form-data">
    Название объявления: <input type="text" name="title"/><br><br>
    Описание объявления: <input type="text" name="description"/><br><br>
    Цена: <input type="number" name="price"/><br><br>
    Первая фотография: <input type="file" name="imageFile"/><br><br>
    <input type="submit" value="Добавить товар"/>
  </form>

</body>

</html>