let textArea = document.getElementById('source');

document.getElementById('data').addEventListener("change", (event) => {
    let files = document.getElementById('data').files;
    const fileReader = new FileReader();
    fileReader.onload = function (e) {
        let content = fileReader.result.toString();
        console.log(content);
        textArea.textContent = content;
    };
    
    fileReader.readAsText(files[0]);
});

//function onFileSelect(input) {
//    let file = input.files[0];
//    const reader = new FileReader();
//
//    reader.onload = (e) => {
//        let txt = reader.result.toString();
//        console.log(txt);
//        textArea.value = txt;
//        texto = txt;
//        //textArea.textContent = txt;
//    };
//
//    reader.readAsText(file);
//    console.log(texto);
//}