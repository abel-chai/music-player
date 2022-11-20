import request from './request'

export function playMusicAPI(id){
    return request({
        url:`/song/${id}`,
        method:'get',
    })
}

export function bannerAPI(){
    return request({
        url:'/banner/',
        method:'get',
    })
}

export function recommendSonglistAPI(style){
    return request({
        url:`/sheet/${style}`,
        method:'get',
    })
}


export function recommendSongAPI(){
    return request({
        url:'/song/',
        method:'get',
    })
}

export function loginAPI(params) {
    return request({
        url: `/auth/login`,
        method: 'post',
        params
    })
}

export function registerAPI(params) {
    return request({
        url: "/client/register",
        method: 'post',
        params
    })
}

export function putCommentAPI(params) {
    return request({
        url: "/sheetComment/",
        method: 'post',
        headers: {
            token: localStorage.getItem('token')
        },
        params
    })
}

export function addToCollectionAPI(params) {
    return request({
        url: '/myListContain/',
        method: 'post',
        headers: {
            token: localStorage.getItem('token')
        },
        params
    })
}

export function createListAPI(id,sheet) {
    return request({
        url: `/mySheet/${id}`,
        method: 'post',
        headers: {
            token: localStorage.getItem('token')
        },
        params: {...sheet}
    })
}

export function dropFromCollectionAPI(params) {
    return request({
        url: `/myListContain/${params.cid}/${params.sid}/1`,
        method: 'delete',
        headers: {
            token: localStorage.getItem('token')
        },
    })
}

export function myCollectionAPI(id){
    return request({
        url:`/myListContain/${id}/1`,
        method:'get',
        headers: {
            token: localStorage.getItem('token')
        },
    })
}

export function singerAPI(params){
    return request({
        url:`/singer/${params}`,
        method:'get',
    })
}


export function playlistDetailAPI(params){
    return request({
        url:`/sheetContain/pre/${params}`,
        method:'get',
    })    
}

export function songInfoAPI(params){
    return request({
        url:"/song/detail",
        method:'get',
        params
    })      
}

export function commentsAPI(params){
    return request({
        url:`/sheetComment/${params}`,
        method:'get',
    })
}

export function searchAPI(params,key){
    return request({
        url:`/${params}/search/${key}`,
        method:'get',
    })      
}

export function singerSongsAPI(id) {
    return request({
        url: `/song/withSinger/${id}`,
        method: 'get',
    })
}

export function userInfoAPI(id) {
    return request({
        url: `/client/${id}`,
        method: 'get',
        headers: {
            token: localStorage.getItem('token')
        },
    })
}

export function userSongsAPI(id) {
    return request({
        url: `/mySheet/${id}`,
        method: 'get',
        headers: {
            token: localStorage.getItem('token')
        },
    })
}

export function addToListAPI(p) {
    return request({
        url: `/sheetContain/${p.id}/${p.singerName}/${p.songName}`,
        method: 'post',
        headers: {
            token: localStorage.getItem('token')
        },
    })
}

export function changeInfoAPI(params) {
    return request({
        url: `/client/`,
        method: 'put',
        headers: {
            token: localStorage.getItem('token')
        },
        params
    })
}

export function dropSonglistAPI(params) {
    return request({
        url: `/sheet/${params}`,
        method: 'delete',
        headers: {
            token: localStorage.getItem('token')
        },
    })
}