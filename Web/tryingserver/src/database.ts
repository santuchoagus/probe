import mongoose from "mongoose";

if (!process.env.MONGO_CONN_STRING) {
    throw new Error("No connection string for mongodb defined");
}

mongoose.connect(process.env.MONGO_CONN_STRING);