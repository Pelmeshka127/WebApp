<!DOCTYPE html>

<html>
<head>
    <title>Candy Shop</title>
</head>

<body>

<h1>Candy Shop</h1><hr>
<h3>Подробная информация о пользователе ${user.name}</h3>
<b>Номер телефона:</b> ${user.phoneNumber}<br>
<b>Email:</b> ${user.email}<br>

<h4>Товары пользователя ${user.name}</h4>

<#list products as product>
<div>
    <p>
        <b>${product.title}</b> ${product.price} руб. | <a href="/product/${product.id}">Подробнее...</a><br>
        <img src="/previewImage/${product.id}" height="120px"/><br><br>
    </p>
</div>
</#list>

<#if errorMessage??>
    <h2 style="color: red">${errorMessage}</h2>
</#if>

</body>
</html>