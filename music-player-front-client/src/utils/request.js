import axios from 'axios'
import { Message } from 'element-ui'
import store from "../store/index"

let baseURL = ""

if(process.env.NODE_ENV == "development"){
    // baseURL = 'https://3fe9ba4c-6501-4859-bd86-7210e83f9802.mock.pstmn.io/'
    baseURL = 'http://vite.fullcomb.top'
} else {
    baseURL = 'http://localhost:9999'
}

store.state.baseURL = baseURL

const service = axios.create({
    baseURL,
    timeout:5000,
    headers: {
        platform: 'client',
    }
})

service.interceptors.response.use(
    response => {
        if(response.status == 200) {
            return Promise.resolve(response)
        }else{
            return Promise.reject(response)
        }
    },
    error => {
        error && Message({
            type:'error',
            message:'网络连接出问题了~',
            showClose:true
        })
    }
)

export default service
