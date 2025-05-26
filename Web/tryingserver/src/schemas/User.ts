import mongoose, {model, Schema, Model} from "mongoose";
import generateRandomId from "../helpers/randomId";

interface UserIface {
    token: String,
    alias?: String,
    // identicon?: number,
}

const UserSchema = new Schema<UserIface>({
    token : {
        type: String,
        required: true,
        unique: true,
        validate: {
            validator: async function(v) {
                const mdl = this.constructor as Model<UserIface>;
                const user = await mdl.findOne({ token: v });
                return user === null || this._id.equals(user._id);
            },
            message: "generated ID not unique"
        }
    },

    alias : {
        type: String,
        //required: true,
        //unique: true,
    },
});

const UserModel = model<UserIface>('User', UserSchema);

async function createNewUser() {
    var unique : boolean = false;

    while(!unique) {
        try {
            const user = new UserModel({ token : generateRandomId() });
            await user.validate();
            unique = true;
            console.log("Created user!", user);
        } catch (err) {
            console.log("Unable to generate ID");
        }
    }
}

createNewUser();