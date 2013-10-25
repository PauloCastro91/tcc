/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function aplicarMascara(src, mask, forbidden) {
    alert("teste");
    if (window.event.keyCode == forbidden.charCodeAt(0)) {
        return false;
    }

    var i = src.value.length;
    var saida = mask.substring(0, 1);
    var texto = mask.substring(i)
    if (texto.substring(0, 1) != saida) {
        src.value += texto.substring(0, 1);
    }
}
