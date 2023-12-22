export const Varient = (value) => {
    // console.log(value);
    if (value <= 20) {
      return "danger";
    } else if (value > 20 && value <= 69) {
      return "warning";
    } else if (value >= 70 && value <= 99) {
      return "info";
    } else if (value === 100) {
      return "success";
    } else {
      return "success";
    }
  };