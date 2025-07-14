import React from 'react';
import {useGlobalLoading} from '../../context/LoadingContext';
import HamsterLoader from '../loader/HamsterLoader';

export default function GlobalLoaderOverlay() {
    const {isLoading} = useGlobalLoading();

    if (!isLoading) return null;

    return (<div style={{
        position: 'fixed',
        top: 0,
        left: 0,
        width: '100vw',
        height: '100vh',
        background: 'rgba(255,255,255,0.85)',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        zIndex: 9999
    }}>
        <HamsterLoader/>
    </div>);
}
