
let textArea = document.getElementById('source');

document.getElementById('data').addEventListener("change", (event) => {
    let files = document.getElementById('data').files;
    const fileReader = new FileReader();
    fileReader.onload = function (e) {
        let content = fileReader.result.toString();
        console.log(content);
        textArea.innerHTML = content;
    };
    
    fileReader.readAsText(files[0]);
});
