'use client'
import { ACCESS_TOKEN } from "@/lib/constants"
import clientCookies from "@/lib/storage/cookie.client"
import { useEffect, useState } from "react"

export const Test = () => {
    //const [token1, setToken1] = useState('');

    // useEffect(() => {
    //     const handle = () => {
    //         setToken1(cookieStore.get(ACCESS_TOKEN) || 'falsy')
    //     }

    //     handle();
    // })

    // return (
    //     <div>
    //         {`token1: ${token1}`}
    //     </div>
    // )

    const token2 = clientCookies.get(ACCESS_TOKEN);

    return (
        <div>
            {`token2: ${token2}`}
        </div>
    )
}