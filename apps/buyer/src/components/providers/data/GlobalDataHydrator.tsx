// components/providers/data/GlobalDataHydrator.tsx
"use client";
import { actions } from "@/store";
import { useAppDispatch } from "@/store/hooks";
import { TAddress } from "@/types/address.types";
import { TUser } from "@/types/auth.types";
import { useEffect } from "react";

type TGlobalDataHydratorProps = {
    currentUser: TUser;
    defaultAddress: TAddress;
}

export const GlobalDataHydrator = ({ currentUser, defaultAddress }: TGlobalDataHydratorProps) => {
    const dispatch = useAppDispatch();

    useEffect(() => {
        dispatch(actions.auth.setCurrentUser(currentUser));
        dispatch(actions.address.setDefaultAddress(defaultAddress));
        console.log(">>> GlobalDataHydrator: init successfully");
    }, [currentUser, defaultAddress, dispatch]);

    return null;
};