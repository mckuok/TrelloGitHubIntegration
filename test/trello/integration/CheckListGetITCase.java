package trello.integration;

import trello.Trello;
import trello.TrelloHttpClient;
import trello.domain.CheckList;
import trello.impl.TrelloImpl;
import trello.impl.http.ApacheHttpClient;
import trello.impl.http.AsyncTrelloHttpClient;
import trello.impl.http.RestTemplateHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(Parameterized.class)
public class CheckListGetITCase {

    private static final String TEST_APPLICATION_KEY = "db555c528ce160c33305d2ea51ae1197";
    public static final String CHECK_LIST_ID = "51990272b1740a191800e5af";

    private Trello trello;

    private TrelloHttpClient httpClient;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{new ApacheHttpClient()}, {new AsyncTrelloHttpClient()}, {new RestTemplateHttpClient()}});
    }

    public CheckListGetITCase(TrelloHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Before
    public void setUp() {
        trello = new TrelloImpl(TEST_APPLICATION_KEY, "", httpClient);
    }

    @Test
    public void testGetCheckList(){
        CheckList checkList = trello.getCheckList(CHECK_LIST_ID);

        assertThat(checkList).isNotNull();
        assertThat(checkList.getId()).isEqualTo(CHECK_LIST_ID);
    }
}
