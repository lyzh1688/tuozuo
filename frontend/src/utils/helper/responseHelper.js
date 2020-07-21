export function success (response) {
    return String(response.code) === '0'
}
export function errorMessage (response) {
    return response.code + ':' + response.msg
}
