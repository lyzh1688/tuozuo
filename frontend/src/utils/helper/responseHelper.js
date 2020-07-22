export function success (response) {
    return String(response.code) === '0'
}
export function errorMessage (response) {
    return response.code + ':' + response.msg
}
export function needLogin (response) {
    return String(response.code) === '1999'
}
