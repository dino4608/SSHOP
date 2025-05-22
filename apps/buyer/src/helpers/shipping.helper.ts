// helpers/shipping.helper.ts

export const getDeliveryDateRange = () => {
    const today = new Date();
    const startDate = new Date(today);
    startDate.setDate(today.getDate() + 3);
    const endDate = new Date(today);
    endDate.setDate(today.getDate() + 5);

    const startDay = startDate.getDate();
    const endDay = endDate.getDate();
    const month = endDate.toLocaleString('default', { month: 'long' });

    return `${startDay} - ${endDay} ${month}`;
};