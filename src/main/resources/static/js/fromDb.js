"use strict";
import {byId, show, deleteChildElementsOf, hide} from "./util.js";

const viewButton = byId("view");

viewButton.addEventListener("click", async function() {
    const response = await fetch(`combinations`);
    if (response.ok){
        hide("malfunction");
        show("combinationsTable");
        const combinations = await response.json();
        const combinationsBody = byId("combinationsBody");
        deleteChildElementsOf(combinationsBody);
        for (const combination of combinations){
            const tr = combinationsBody.insertRow();
            tr.insertCell().innerText = combination.id;
            tr.insertCell().innerText = combination.combination;
        }
    } else {
        show("malfunction");
    }

});