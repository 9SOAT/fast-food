package com.fiap.challenge.food.domain.model.order;

import com.fiap.challenge.food.domain.model.exception.UnprocessableEntityException;

import java.util.List;

public enum OrderStatus {
    WAITING_PAYMENT {
        @Override
        public OrderStatus next() {
            return READY_FOR_PREPARATION;
        }
    },
    READY_FOR_PREPARATION {
        @Override
        public OrderStatus next() {
            return IN_PREPARATION;
        }
    },
    IN_PREPARATION {
        @Override
        public OrderStatus next() {
            return READY_FOR_PICKUP;
        }
    },
    READY_FOR_PICKUP {
        @Override
        public OrderStatus next() {
            return FINISHED;
        }
    },
    FINISHED {
        @Override
        public OrderStatus next() {
            throw new UnprocessableEntityException("No next status available for FINISHED orders.", "INVALID_ORDER_TRANSITION");
        }
    };

    public abstract OrderStatus next();

    public static List<OrderStatus> getInProgressStatuses() {
        return List.of(READY_FOR_PREPARATION, IN_PREPARATION, READY_FOR_PICKUP);
    }

    public boolean isWaitingPayment() {
        return this == WAITING_PAYMENT;
    }

    public boolean isInProgress() {
        return getInProgressStatuses().contains(this);
    }
}


