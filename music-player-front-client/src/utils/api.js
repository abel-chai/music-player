import request from './request'
import store from '../store/index'

export function getNewSongsAPI(params){
    return request({
        url:'/top/song',
        method:'get',
        headers: {
            token: store.state.token
        },
        params
    })
}

export function playMusicAPI(id){
    return request({
        url:`/song/${id}`,
        method:'get',
        headers: {
            token: store.state.token
        },
    })
}

export function bannerAPI(){
    return request({
        url:'/banner',
        method:'get',
        headers: {
            token: store.state.token
        },
    })
}

export function recommendSonglistAPI(style){
    return request({
        url:`/sheet${style}`,
        method:'get',
        headers: {
            token: store.state.token
        },
    })
}


export function recommendSongAPI(){
    return request({
        url:'/song',
        method:'get',
        headers: {
            token: store.state.token
        },
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
        url: "​/client​/register",
        method: 'post',
        params
    })
}

export function putCommentAPI(params) {
    return request({
        url: "​/sheetComment",
        method: 'post',
        headers: {
            token: store.state.token
        },
        params
    })
}

export function addToCollectionAPI(params) {
    return request({
        url: "/myListContain",
        method: 'post',
        headers: {
            token: store.state.token
        },
        params
    })
}

export function singerAPI(params){
    return request({
        url:`/singer/${params}`,
        method:'get',
        headers: {
            token: store.state.token
        },
    })
}


export function playlistDetailAPI(params){
    return request({
        url:`/sheetContain/pre/${params}`,
        method:'get',
        headers: {
            token: store.state.token
        },
    })    
}

export function songInfoAPI(params){
    return request({
        url:"/song/detail",
        method:'get',
        headers: {
            token: store.state.token
        },
        params
    })      
}

export function commentsAPI(params){
    return request({
        url:`/sheetComment/${params}`,
        method:'get',
        headers: {
            token: store.state.token
        },
    })
}

export function searchAPI(params,key){
    return request({
        url:`/${params}/search/${key}`,
        method:'get',
        headers: {
            token: store.state.token
        },
    })      
}

export function singerSongsAPI(id) {
    return request({
        url: `/song/withSinger/${id}`,
        method: 'get',
        headers: {
            token: store.state.token
        },
    })
}

export function userInfoAPI(id) {
    return request({
        url: `/client/${id}`,
        method: 'get',
        headers: {
            token: store.state.token
        },
    })
}

export function userSongsAPI(id) {
    return request({
        url: `/mysheet/${id}`,
        method: 'get',
        headers: {
            token: store.state.token
        },
    })
}

export function changeInfoAPI(params) {
    return request({
        url: `/client`,
        method: 'put',
        headers: {
            token: store.state.token
        },
        params
    })
}

export function dropSonglistAPI(params) {
    return request({
        url: `/sheet/${params}`,
        method: 'delete',
        headers: {
            token: store.state.token
        },
    })
}