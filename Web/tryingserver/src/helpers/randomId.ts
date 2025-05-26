import "crypto";

export default function generateRandomId() : String {
    const int8arr = new Uint8Array(32);
    crypto.getRandomValues(int8arr);

    return Array.from(int8arr)
    .map(b => b.toString(16).padStart(2, '0'))
    .join('')
}