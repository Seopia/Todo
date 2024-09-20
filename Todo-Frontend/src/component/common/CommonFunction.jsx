import api from "../../AxiosInterceptor"


export const darkModeText = 'white';

export const setStyle = (isDark, object) => {
    if(object==='text'){
        if(isDark){
            return 'white';
        } else {
            return 'black';
        }
    } else if(object === 'container'){
        if(isDark){
            return '#494d5c';
        } else {
            return 'white';
        }
    }
}

export const getUserProfileImage = async () => {
    const res = await api.get(`/file/profile`);
    return res;
}