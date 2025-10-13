package app.services.splitit;

import app.dto.UserBalanceDTO;
import app.entities.splitit.Settlement;
import app.exceptions.DatabaseException;

import java.util.List;
import java.util.Map;

public interface BalanceService {
        public List<UserBalanceDTO>  getGroupBalances(int groupId) throws DatabaseException;
        public List<Settlement> getSettlements(int groupId) throws DatabaseException;
    }


