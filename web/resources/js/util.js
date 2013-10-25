function aplicarMascara(src, mask, forbidden) {
    if (window.event.keyCode == forbidden.charCodeAt(0)) {
        return false;
    }
    var i = src.value.length;
    if (parseInt(i) < 10) {
        var saida = mask.substring(0, 1);
        var texto = mask.substring(i)
        if (texto.substring(0, 1) != saida) {
            src.value += texto.substring(0, 1);
        }
    } else {
        src.value = ((src.value).toString().substring(0, 9));
    }

}