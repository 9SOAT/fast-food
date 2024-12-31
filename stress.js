import http from 'k6/http'
import {check, sleep} from 'k6'
import {randomIntBetween} from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';


export const options = {
    stages: [
        {duration: '1m', target: 100},
        {duration: '2m', target: 150},
        {duration: '2m', target: 180},
        {duration: '2m', target: 180},
        {duration: '2m', target: 0}
    ]
}
export default function () {
    let productId = randomIntBetween(1, 100)
    let res = http.get('http://localhost:8080/products/' + productId)
    check(res, {'success login': (r) => r.status === 204})
    sleep(0.3)
}
