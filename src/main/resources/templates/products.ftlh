<!DOCTYPE html>
<html>

<head>
  <title>Candy Shop</title>
  <link rel="stylesheet" type="text/css" href="products.css">
</head>

<body>

<h1>Candy Shop</h1><hr>
  <img src="/sourceimages/main.jpg" height="400px" width="350px"/><br><br>
<hr>
<#if user.email??>
  <h3>Имя пользователя: <i>${user.name}</i></h3>
  <form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Выйти"/>
  </form>
  <#if user.isAdmin()>
      <a href="/admin">Панель администратора</a>
  </#if>
<#else>
  <a href="/login">Войти</a></h1>
</#if>
<hr>
<form action="/" method="get">
<#list products as product>
  <div>
    <p>
      <b>${product.title}</b> ${product.price} руб. |
      <a href="/product/${product.id}">Подробнее...</a><br>
      <img src="/previewImage/${product.id}" height="120px"/><br><br>
    </p>
  </div>
<#else>
<h3>Товаров нет</h3>
</#list>
</form>

<#if user.email??>
<hr>
<h3>Создать новый товар</h3>
  <form action="/product/create" method="post" enctype="multipart/form-data">
    Название объявления: <input type="text" name="title"/><br><br>
    Описание объявления: <input type="text" name="description"/><br><br>
    Цена: <input type="number" name="price"/><br><br>
    Первая фотография: <input type="file" name="imageFile"/><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Добавить товар"/>
  </form>
</#if>

</body>

</html>