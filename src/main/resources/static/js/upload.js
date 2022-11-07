$(document).ready(function () {
    $('#upload-category').change(function () {
        let select = $(this).val();
        if (select == '1') {
            $('#upload-subcategory').html(
                '<option value="" disabled>Select sub Category</ooption>' +
                '<option value="coat">Coat</ooption>' +
                '<option value="jacket">Jacket</ooption>' +
                '<option value="shirt">Shirt</ooption>' +
                '<option value="tops">Tops</ooption>' +
                '<option value="knitwear">Knitwear</ooption>' +
                '<option value="trousers">Trousers</ooption>' +
                '<option value="jeans">Jeans</ooption>');
        } else if (select == '2') {
            $('#upload-subcategory').html(
                '<option value="" disabled>Select sub Category</ooption>' +
                '<option value="coat">Coat</ooption>' +
                '<option value="jacket">Jacket</ooption>' +
                '<option value="shirt">Shirt</ooption>' +
                '<option value="tops">Tops</ooption>' +
                '<option value="knitwear">Knitwear</ooption>' +
                '<option value="skirts">Skirts</ooption>' +
                '<option value="dresses">Dresses</ooption>' +
                '<option value="trousers">Trousers</ooption>' +
                '<option value="jeans">Jeans</ooption>');
        } else if (select == '3') {
            $('#upload-subcategory').html(
                '<option value="" disabled>Select sub Category</ooption>' +
                '<option value="boots">Boots</ooption>' +
                '<option value="mules">Mules</ooption>' +
                '<option value="shoes">Shoes</ooption>' +
                '<option value="sneakers">Sneakers</ooption>');
        } else if (select == '4') {
            $('#upload-subcategory').html(
                '<option value="" disabled>Select sub Category</ooption>' +
                '<option value="necklace">Necklace</ooption>' +
                '<option value="belts">Belts</ooption>' +
                '<option value="bags">Bags</ooption>' +
                '<option value="eyewear">Eyewear</ooption>' +
                '<option value="hats">Hats</ooption>' +
                '<option value="jewelery">Jewelery</ooption>');
        }
    });
});