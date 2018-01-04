package palash.watermelon.letsremind.utility;

/*
 * Created by 849501 on 10/30/2017.
 */

public interface Mapper<Input, Output> {
    Output mapTo(Input input);
}
