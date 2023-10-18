import { differenceInMinutes, differenceInHours, differenceInDays, format } from 'date-fns';

export const formatDate = (dateString) => {
  console.log(dateString);
  const date = new Date(dateString);
  const now = new Date();
  console.log(date);

  const diffMinutes = differenceInMinutes(now, date);
  const diffHours = differenceInHours(now, date);
  const diffDays = differenceInDays(now, date);  

  if (diffMinutes < 60) {
    return `${diffMinutes}분 전`;
  } else if (diffHours < 24) {
    return `${diffHours}시간 전`;
  } else if (diffDays < 365) {
    return format(date, 'MM/dd');
  } else {
    return format(date, 'yyyy/MM/dd');
  }
};