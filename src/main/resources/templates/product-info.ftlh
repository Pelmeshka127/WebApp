<!DOCTYPE html>
<html>

<head>
  <title>Candy Shop</title>
</head>

<body>

  <h1>Candy Shop</h1><hr>
  <h4>Подробная информация о товаре</h4>

  <#list images as img>
    <img src="/images/${img.id}" height="120px"/><br><br>
  </#list>

  <b>Название товара: </b>${product.title}<br>
  <b>Описание товара: </b>${product.description}<br>
  <b>Цена: </b>${product.price}<br>
  <hr>

  <form action="/product/addimage/${product.id}" method="post" enctype="multipart/form-data">
    Фотография: <input type="file" name="file1"/><br><br>
    <input type="submit" value="Добавить фото"/>
  </form><hr>

  <form action="/product/delete/${product.id}" method="post">
    <input type="submit" value="Удалить товар"/>
  </form>

</body>

</html>