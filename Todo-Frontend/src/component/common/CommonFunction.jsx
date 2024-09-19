import api from "../../AxiosInterceptor"

export const getUserProfileImage = async () => {
    const res = await api.get(`/file/profile`);
    return res;
}