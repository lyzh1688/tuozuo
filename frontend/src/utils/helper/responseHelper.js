export function success (response) {
    console.log(response)
    return String(response.code) === '0'
}
export function errorMessage (response) {
    return response.code + ':' + response.msg
}
