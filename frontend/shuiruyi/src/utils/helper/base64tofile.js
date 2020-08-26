function dataURLtoBlob (dataurl) {
    var arr = dataurl.split(',')
        var mime = arr[0].match(/:(.*?);/)[1]
        var bstr = atob(arr[1])
        var n = bstr.length
        var u8arr = new Uint8Array(n)
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n)
    }
    return new Blob([u8arr], { type: mime })
}
// 将blob转换为file
function blobToFile (theBlob, fileName) {
   theBlob.lastModifiedDate = new Date()
   theBlob.name = fileName
   return theBlob
}
function base64ToFile (dataurl, imgName) {
    var blob = dataURLtoBlob(dataurl)
var file = blobToFile(blob, imgName)
    return file
 }
// 调用

export default base64ToFile
