"use strict";
import {byId, show, deleteChildElementsOf, hide} from "./util.js";

const lengthSelect = byId("length");
const generateButton = byId("generate");
const fileInput = byId("fileInput")
const saveButton = byId("saveButton");

generateButton.addEventListener("click", async () => {
    const selectedValue = lengthSelect.value;
    const formData = new FormData();
    formData.append('file', fileInput.files[0]);
    formData.append("length", selectedValue);
    const response = await fetch('/file/process-file', {
        method:'POST',
        body: formData
    });
    if (response.ok){
        hide("malfunction");
        show("combinations");
        const combinations = await response.json();
        const ul = byId("combinations");
        deleteChildElementsOf(ul);
        for (const word of combinations){
            const li = document.createElement("li");
            li.textContent=word;
            ul.appendChild(li);
        }
        saveButton.disabled = false;

    } else {
        show("malfunction");
    }
});
    saveButton.addEventListener("click", async () => {
        generateButton.click();
        await new Promise(resolve => setTimeout(resolve, 1000));
        const combinations = Array.from(document.querySelectorAll('#combinations li')).map(li => li.textContent);

        const saveResponse = await fetch('/combinations/save-combinations', {
            method: 'POST',
            body: JSON.stringify(combinations),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (saveResponse.ok) {
           show("success");
        } else {
            show("malfunction")
        }
    });
