import {createContext, useContext, useState} from 'react';

const LoadingContext = createContext();

export function LoadingProvider({children}) {
    const [isLoading, setIsLoading] = useState(false);

    return (<LoadingContext.Provider value={{isLoading, setIsLoading}}>
        {children}
    </LoadingContext.Provider>);
}

export function useGlobalLoading() {
    return useContext(LoadingContext);
}
