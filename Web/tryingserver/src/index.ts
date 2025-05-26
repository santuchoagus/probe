import express, { Express } from "express";

const expressServer : Express = express();
const PORT : number = Number(process.env.PORT || "3030");

expressServer.listen(PORT, () => {
    console.log(`Server listening at http://localhost:${PORT}/`);
});