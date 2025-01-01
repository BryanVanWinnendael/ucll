const option = {
    animation : true,
    delay: 2000,
}

if(localStorage.getItem("added") === "true"){
    const toastHTMLElement = document.getElementById("toasty")
    const toastElement = new bootstrap.Toast(toastHTMLElement,option)
    toastElement.show()
    Toasty()
    localStorage.setItem("added",false)
}

function Toasty(){
    const toastHTMLElement = document.getElementById("toasty")
    const toastElement = new bootstrap.Toast(toastHTMLElement,option)
    toastElement.show()
}

